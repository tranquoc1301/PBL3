import mysql.connector
from venv import logger


def save_company_into_DB(data):
    try:
        connection = mysql.connector.connect(user='root', password='Hoc12345', host='localhost',
                                             database='pbl3_database')
        cursor = connection.cursor()
        query = "INSERT INTO company (COMPANY_NAME, LOCATION, STAFF_SIZE, COMPANY_DESCRIPTION) VALUES (%s, %s, %s, %s)"
        for i in data:
            getCompanyId = "SELECT ID FROM company WHERE COMPANY_NAME = '" + str(i[0]) + "'"
            cursor.execute(getCompanyId)
            id = cursor.fetchone()
            if id is None:
                cursor.execute(query, i)

        connection.commit()
        connection.close()
    except Exception as e:
        logger.error(f"Error occured while saving data to DB: {e}")


def save_job_into_DB(data):
    try:
        connection = mysql.connector.connect(user='root', password='Hoc12345', host='localhost',
                                             database='pbl3_database')
        cursor = connection.cursor()
        query = "INSERT INTO pbl3_database.job(COMPANY_ID, JOB_NAME, COMPANY_LOCATIONS, POSTED_DATE, ENROLLMENT_LOCATION, `ROLE`, SALARY, GENDER_REQUIREMENT, NUMBER_OF_RECRUITMENT,  AGE_REQUIREMENT, PROBATION_TIME,  WORKWAY, EXPERIENCE_REQUIREMENT, DEGREE_REQUIREMENT, BENEFITS, JOB_DESCRIPTION, JOB_REQUIREMENT, DEADLINE, SOURCE_PICTURE) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
        for i in data:
            getCompanyId = "SELECT ID FROM company WHERE COMPANY_NAME = '" + str(i[0]) + "'"
            companyId = get_company_id_FromDB(getCompanyId)
            tmp = []
            tmp.append(companyId)
            tmp.extend(i[1:19])
            cursor.execute(query, tmp)

        connection.commit()
        connection.close()
    except Exception as e:
        logger.error(f"Error occurred while saving data to DB: {e}")


def get_company_id_FromDB(data):
    try:
        connection = mysql.connector.connect(user='root', password='Hoc12345', host='localhost',
                                             database='pbl3_database')
        cursor = connection.cursor()
        cursor.execute(data)
        res = cursor.fetchone()
        res = ''.join(str(x) for x in res)
        return res
    except Exception as e:
        logger.error(f"Error occured while saving data to DB: {e}")