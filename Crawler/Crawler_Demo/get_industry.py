from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By

# Khởi tạo trình duyệt Chrome với chế độ headless
chrome_options = Options()
chrome_options.add_argument('--headless')
DRIVER_PATH = 'C:/Users/tranq/PycharmProjects/Crawler/chromedriver-win32/chromedriver'  # Đường dẫn đến tệp "chromedriver"
driver = webdriver.Chrome(options=chrome_options)

# Truy cập trang web
url = 'https://vieclam24h.vn/tim-kiem-viec-lam-nhanh'
driver.get(url)

# Lấy dữ liệu từ trang web

# Sử dụng XPath chính xác để tìm và nhấp vào phần tử
work_location = driver.find_element(By.XPATH,
                                    '/html/body/div[3]/div/header/div[1]/div[2]/div/div/div[2]/div[1]/div[1]/div/div[1]/button')
work_location.click()
location_div = driver.find_element(By.CLASS_NAME, 'jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
print(location_div)
# work_location_detail = driver.find_elements(By.CLASS_NAME,
#                                             'flex items-center w-full text-sm h-10 px-3 py-2 rounded transition-all cursor-pointer hover:bg-[#E5F2FF]')
# for location in work_location_detail:
#     print(location.text)
