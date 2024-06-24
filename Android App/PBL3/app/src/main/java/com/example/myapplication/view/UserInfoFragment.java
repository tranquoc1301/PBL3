package com.example.myapplication.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.ApiService;
import com.example.myapplication.controller.CVRecycleViewAdapter;
import com.example.myapplication.databinding.DialogChangePassBinding;
import com.example.myapplication.databinding.FragmentUserInfoBinding;
import com.example.myapplication.models.CV;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.PostUser;
import com.example.myapplication.models.User;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private User user;
    private FragmentUserInfoBinding binding;

    private ActivityResultLauncher<String> mGetContent;



    private boolean isEditBtnClick = false;
    public UserInfoFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String userJsonParam) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, userJsonParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam = getArguments().getString(ARG_PARAM);
            user = new Gson().fromJson(mParam, User.class);
        }
    }

    public void showCV() {
        ApiService.apiService.getCvByUserId(user.getId()).enqueue(new Callback<List<CV>>() {
            @Override
            public void onResponse(Call<List<CV>> call, Response<List<CV>> response) {
                if (response.isSuccessful()) {
                    List<CV> list = response.body();
                    CVRecycleViewAdapter adapter = new CVRecycleViewAdapter(list, new CVRecycleViewAdapter.OnClickListener() {
                        @Override
                        public void onClick(View view, int pos) {
                            String url = ApiService.url + "/cv/my-cv?id=" + list.get(pos).getId();
                            Intent intent = new Intent(getContext(), PdfViewActivity.class);
                            intent.putExtra(PdfViewActivity.STR_BUNDLE, url);
                            startActivity(intent);
                        }
                    });

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    binding.recyclerView.setAdapter(adapter);
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<CV>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Fails on call API", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void convertToFile(InputStream inputStream, String destination) throws IOException {
        OutputStream outputStream = null;
        try
        {
            File file = new File(destination);
            outputStream = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally
        {
            if(outputStream != null)
            {
                outputStream.close();
            }
        }
    }








    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);

        showCV();

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @SuppressLint("Recycle")
            @Override
            public void onActivityResult(Uri uri) {

                try {
                    Log.e(TAG, "onActivityResult: " + getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
                    ContentResolver contentResolver = getContext().getContentResolver();
                    InputStream inputStream = contentResolver.openInputStream(uri);
                    String fileName = "/user_cv_" + user.getId() + ".pdf";
                    String destination = getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + fileName;
                    convertToFile(inputStream, destination);
                    File file = new File(destination);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                    ApiService.apiService.uploadCV(part, user.getId()).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "OKKKKKKKKKKKKKKKKKk", Toast.LENGTH_LONG).show();
                                showCV();
                            }
                            else {
                                Toast.makeText(getContext(), "Your file name has unicode", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Fails on call api", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                catch (Exception e) {
                    Log.e(TAG, "onActivityResult: " + e.getMessage());

                }
            }
        });


        binding.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mGetContent.launch("application/pdf");
            }
        });


        binding.nameEt.setText( user.getName());
        binding.emailEt.setText(user.getEmail());
        binding.selectDate.setText(user.getDate_of_birth().substring(0,10));
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource( binding.getRoot().getContext(), R.array.gender_string_in_edit, R.layout.list_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.selectGender.setAdapter(genderAdapter);
        for (int i = 0; i < genderAdapter.getCount(); i++) {
            if (Objects.equals(user.getGender(), genderAdapter.getItem(i).toString())) {
                binding.selectGender.setSelection(i);
                break;
            }
        }

        binding.avatarUrl.setText(user.getAvatar_scr());
        if (!Objects.equals(user.getAvatar_scr(), "") && user.getAvatar_scr() != null) {
            Picasso.get().load(user.getAvatar_scr()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).resize(150,150).onlyScaleDown().into(binding.avatar);
        }


        binding.selectDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    binding.selectDate.setText(i2 + "-" + i1 + "-" + i);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                    return true;
                }
                return false;
            }


        });



        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(isEditBtnClick)) {
                    binding.editBtn.setText("Ok");
                    binding.nameEt.setEnabled(true);
                    binding.selectGender.setEnabled(true);
                    binding.selectDate.setEnabled(true);
                    binding.avatarUrl.setEnabled(true);
                    isEditBtnClick = true;

                }
                else {
                    if (binding.nameEt.getText().toString().equals("")) {

                        binding.nameTv.setText(Html.fromHtml("Name <font color=\"red\">This text is blue.</font>"), TextView.BufferType.SPANNABLE);
                        return;
                    }
                    String name = binding.nameEt.getText().toString();
                    String dateOfBirth = binding.selectDate.getText().toString();
                    String email = binding.emailEt.getText().toString();
                    String gender = binding.selectGender.getSelectedItem().toString();
                    String avatar = binding.avatarUrl.getText().toString();
                    PostUser postUser = new PostUser(name, email, dateOfBirth, gender, avatar);
                    ApiService.apiService.updateUser(user.getId(), postUser).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                binding.editBtn.setText("EDIT");
                                binding.nameEt.setEnabled(false);
                                binding.selectGender.setEnabled(false);
                                binding.selectDate.setEnabled(false);
                                binding.avatarUrl.setEnabled(false);
                                binding.emailEt.setEnabled(false);
                                isEditBtnClick = false;
                                ((MainActivity)getActivity()).userUpdate();
                                if (!binding.avatarUrl.getText().toString().equals("")) {
                                    Picasso.get().load(binding.avatarUrl.getText().toString()).placeholder(R.drawable.load_animation).error(R.mipmap.ic_launcher).resize(480,480).onlyScaleDown().into(binding.avatar);
                                }
                            }
                            else {
                                Message message = new Gson().fromJson(response.errorBody().charStream(), Message.class);
                                if (message.getMessage() != null) {
                                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getContext(), message.getError().get(0).getMsg(), Toast.LENGTH_LONG).show();
                                }
                                return;
                            }
                        }



                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Fail on call API", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        binding.changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)UserInfoFragment.this.getActivity();
                Dialog dialog = new Dialog(mainActivity);
                DialogChangePassBinding dialogChangePassBinding = DialogChangePassBinding.inflate(getLayoutInflater());
                dialog.setContentView(dialogChangePassBinding.getRoot());
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialogChangePassBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });


                dialog.show();
            }
        });



        return binding.getRoot();
    }


}