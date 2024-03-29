const express = require('express');
const { check, validationResult } = require('express-validator');
const bcrypt = require('bcrypt');
const bodyParser = require('body-parser');
const db = require('./db');
const port = 3000;

const app = express();
app.use(bodyParser.json());

app.post('/register', [
    check('name').notEmpty(),
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 }),
    check('confirmPassword').custom((value, { req }) => {
        if (value !== req.body.password) {
            throw new Error('Passwords do not match');
        }
        return true;
    })
], (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(422).json({ errors: errors.array() });
    }

    const { name, email, password } = req.body;

    // Kiểm tra xem người dùng đã tồn tại hay chưa
    const checkUserQuery = 'SELECT * FROM USER WHERE EMAIL = ?';
    db.query(checkUserQuery, [email], (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Failed to register user !' });
        }

        // Nếu đã tồn tại người dùng có cùng email trong cơ sở dữ liệu
        if (results.length > 0) {
            return res.status(409).json({ error: 'User already exists !' });
        }

        // Nếu không có người dùng nào cùng email tồn tại, tiến hành đăng ký
        bcrypt.hash(password, 10, (err, hashedPassword) => {
            if (err) {
                return res.status(500).json({ error: 'Failed to register user !' });
            }

            const insertUserQuery = 'INSERT INTO USER (NAME, EMAIL, PASSWORD, PRIVILEGE_ID, CREATE_AT) VALUES (?, ?, ?, 0, NOW())';
            db.query(insertUserQuery, [name, email, hashedPassword], (err, result) => {
                if (err) {
                    return res.status(500).json({ error: 'Failed to register user !' });
                }
                res.status(201).json({ message: 'User registered successfully !' });
            });
        });
    });
});


app.post('/login', [
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 })
], (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(422).json({ errors: errors.array() });
    }

    const { email, password } = req.body;

    // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu không
    const getUserQuery = 'SELECT * FROM USER WHERE EMAIL = ?';
    db.query(getUserQuery, [email], (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Failed to authenticate user !' });
        }

        if (results.length === 0) {
            return res.status(401).json({ error: 'User not found !' });
        }

        const user = results[0];

        // So sánh mật khẩu đã hash với mật khẩu người dùng cung cấp
        bcrypt.compare(password, user.PASSWORD, (err, isMatch) => {
            if (err) {
                return res.status(500).json({ error: 'Failed to authenticate user !' });
            }

            if (!isMatch) {
                return res.status(401).json({ error: 'Invalid email or password !' });
            } else
                res.status(200).json({ message: 'Login successful !' });
        });
    });
});

app.listen(port, () => {
    console.log(`Server started on port ${port}`);
});
