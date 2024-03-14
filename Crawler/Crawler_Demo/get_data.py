from venv import logger
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup

def get_company_name(source):
    return source.find('h3', class_='font-normal text-16 text-se-neutral-64 mb-4').get_text(' ', strip=True)


def get_job_title(source):
    return source.find('h1', class_='font-semibold text-18 md:text-24 leading-snug').get_text(' ', strip=True)


def get_job_industry(source):
    return source.find('p', class_='text-14 text-se-accent font-semibold').get_text(' ', strip=True)


def get_company_location(source):
    div = source.find_all('div', class_='text-14 text-se-grey-48 font-semibold')
    return div[0].get_text(' ', strip=True)


def get_company_staff_size(source):
    div = source.find_all('div', class_='text-14 text-se-grey-48 font-semibold')
    return div[1].get_text(' ', strip=True)


def get_company_description(source):
    div_element = source.find('div', class_='max-h-[84px] overflow-hidden mt-4 text-14 text-se-neutral-84 mb-2')
    p_element = div_element.find('p')
    return p_element.get_text(' ', strip=True)


def get_NumEmployee(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
    if len(div[1].find_all('div', class_='flex items-center mb-4 md:w-[33%]')) == 1:
        num_div = div[1].find('div', class_='flex items-center mb-4 md:w-[33%]')
        num_of_employee = num_div.find('p', class_='text-14').get_text(' ', strip=True)
    elif len(div[1].find_all('div', class_='flex items-center mb-4 md:w-[33%]')) == 2:
        num_div = div[1].find_all('div', class_='flex items-center mb-4 md:w-[33%]')
        num_of_employee = num_div[1].find('p', class_='text-14').get_text(' ', strip=True)
    return num_of_employee


def get_experience_requirement(source):
    div = source.find_all('div', class_='flex items-center mb-4 w-full md:w-[33%]')
    return div[2].find('p', class_='text-14').get_text(' ', strip=True)


# Continue
def get_role(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
    divv = div[0].find_all('div', class_='flex items-center mb-4 md:w-[33%]')
    if len(div[0].find_all('div', class_='flex items-center mb-4 md:w-[33%]')) == 2:
        div_level = divv[1]
        level = div_level.find('p', class_='text-14').get_text(' ', strip=True)
    elif len(div[0].find_all('div', class_='flex items-center mb-4 md:w-[33%]')) == 3:
        div_level = divv[2]
        level = div_level.find('p', class_='text-14').get_text(' ', strip=True)
    return level


def get_salary(source):
    return source.find('p', class_='font-semibold text-14 text-[#8B5CF6]').get_text(' ', strip=True)


def get_edu(source):
    div = source.find_all('div', class_='flex items-center mb-4 w-full md:w-[33%]')
    return div[1].find('p', class_='text-14').get_text(' ', strip=True)


def get_requirement(source):
    div = source.find_all('div',
                          class_='jsx-d84db6a84feb175e mb-2 text-14 break-words text-se-neutral-80 text-description')
    return div[1].get_text(' ', strip=True)


def get_description(source):
    div = source.find_all('div',
                          class_='jsx-d84db6a84feb175e mb-2 text-14 break-words text-se-neutral-80 text-description')
    return div[0].get_text(' ', strip=True)


def get_deadline_enrollment(source):
    div = source.find_all('div', class_='ml-3 text-14 md:flex pt-0 md:pt-[5px]')
    date_ = div[1].get_text(' ', strip=True)
    part = date_.find(':')
    return date_[part + 2:]


def get_SrcPic(source):
    div = source.find('div', class_='md:flex w-full items-start')
    return div.find('img').get('src')


def get_posted_date(source):
    div = source.find_all('div', class_='flex items-center mb-4 md:w-[33%]')
    date = div[0].find('p', class_='text-14').get_text(' ', strip=True)
    return date


def get_enrollment_location(source):
    div = source.find_all('div', class_='ml-3 text-14 md:flex pt-0 md:pt-[5px]')
    div_ = div[2].get_text(' ', strip=True)
    part = div_.find(':')
    return div_[part + 2:]


def get_probation(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
    divv = div[0].find_all('div', class_='flex items-center mb-4 md:w-[33%]')
    if len(div[0].find_all('div', class_='flex items-center mb-4 md:w-[33%]')) == 3:
        div_level = divv[1]
        probation = div_level.find('p', class_='text-14').get_text(' ', strip=True)
        return probation
    else:
        return 'None'


def get_gender(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
    if len(div[1].find_all('div', class_='flex items-center mb-4 md:w-[33%]')) == 2:
        num_div = div[1].find_all('div', class_='flex items-center mb-4 md:w-[33%]')
        gender = num_div[0].find('p', class_='text-14').get_text(' ', strip=True)

        return gender
    else:
        return 'None'


def get_work_way(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
    way = div[1].find('div', class_='flex items-center mb-4 w-full md:w-[33%]').find('p', class_='text-14').get_text(
        ' ', strip=True)
    return way


def get_age_requirement(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e md:flex md:border-b border-[#DDD6FE] mb-4')
    if (div[2].find('div', class_='flex items-center mb-4 md:w-[33%]')):
        age = div[2].find('div', class_='flex items-center mb-4 md:w-[33%]').find('p', class_='text-14').get_text(' ',
                                                                                                                  strip=True)
        return age
    else:
        return 'None'


def get_benefit(source):
    div = source.find_all('div', class_='jsx-d84db6a84feb175e mb-2 text-14 break-words text-se-neutral-80 text-description')
    return div[2].get_text(' ', strip=True)
