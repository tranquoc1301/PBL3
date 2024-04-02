from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup
import time

def get_area(source):
    soup = BeautifulSoup(source, 'html.parser')
    div = soup.find('div', class_='overflow-y-auto w-full h-full')
    areas = div.find_all('a', class_='flex items-center w-full text-sm h-10 px-3 py-2 rounded transition-all cursor-pointer text-se-neutral-84 hover:bg-[#E5F2FF]')
    area_texts = [area.get_text(' ', strip=True) for area in areas]
    another_area = []
    another_area.append(div.find('a', class_='flex items-center w-full text-sm h-10 px-3 py-2 rounded transition-all cursor-pointer text-se-neutral-84 bg-[#A6D2FF] hover:bg-[#E5F2FF]').get_text(' ', strip=True))
    another_area.append(div.find('a', class_='flex items-center w-full text-sm h-10 px-3 py-2 rounded transition-all cursor-pointer text-se-neutral-84 border-t rounded-t-none hover:bg-[#E5F2FF]').get_text(' ', strip=True))
    for value in another_area:
        area_texts.append(value)
        
    return area_texts



def crawl_area():
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    driver = webdriver.Chrome(options=chrome_options)
    link = 'https://vieclam24h.vn/'

    driver.get(link)
    time.sleep(3) 
    source = driver.page_source
    area = get_area(source)

    return area

