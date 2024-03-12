from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup

# Khởi tạo trình duyệt Chrome với chế độ headless
chrome_options = Options()
chrome_options.add_argument('--headless')
DRIVER_PATH = './chromedriver'  # Đường dẫn đến tệp "chromedriver"
driver = webdriver.Chrome(options=chrome_options)

# Truy cập trang web
url = 'https://vieclam24h.vn/ban-hang-kinh-doanh/nhan-vien-kinh-doanh-c13p122id200316309.html?open_from=0101_1_1'
driver.get(url)

# Lấy dữ liệu từ trang web
soup = BeautifulSoup(driver.page_source, 'html.parser')
div_element = soup.find('div', class_='max-h-[84px] overflow-hidden mt-4 text-14 text-se-neutral-84 mb-2')
p_element = div_element.find('p')

print(p_element.get_text(' ', strip=True))
