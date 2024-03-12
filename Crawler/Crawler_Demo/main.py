import concurrent.futures
from venv import logger
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from get_information import get_job_data
from DB import *
import get_information
def removeIfDuplicate(data):
    tmp = []
    for i in data:
        if i not in tmp:
            tmp.append(i)
        else:
            continue
    return tmp
def crawl_data():
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    try:
        with webdriver.Chrome(options=chrome_options) as driver:
            data = get_job_data(driver, 2)
            data= list(filter(None, data))  # filter none element
            company_data = []

            for d in data:
                tmp = []
                tmp.append(d[0]) #COMPANY_NAME
                tmp.append(d[2]) #COMPANY_LOCATIONS
                tmp.append(d[19]) #CONPANY_STAFF_SIZE
                tmp.append(d[20]) #COMPANY_DESCRIPTION
                company_data.append(tmp)

            company_data = removeIfDuplicate(company_data)
            save_company_into_DB(company_data)
            save_job_into_DB(data)

    except Exception as e:
        logger.error(f"Error occurred while scraping data: {e}")
    print('Finish scraping !')


if __name__ == '__main__':
    crawl_data()
