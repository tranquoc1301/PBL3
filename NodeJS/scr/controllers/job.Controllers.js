const connect = require('../config/database');
const { validationResult } = require('express-validator');
const { getCompanyList } = require('./company.Controllers');

const getJobList = (req, res) => {
    const query = 'SELECT * FROM JOB';
    connect.query(query, (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        res.json(results);
    });
}

const getJobListByIndustry = (req, res) => {
    const industry_id = req.query.industry_id;

    // Thực hiện truy vấn SQL để lấy tên ngành dựa trên industry_id
    const query = 'SELECT INDUSTRY_NAME FROM INDUSTRY WHERE INDUSTRY_ID = ?';
    connect.query(query, [industry_id], (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        // Kiểm tra xem có kết quả từ truy vấn hay không
        if (results.length === 0) {
            res.status(404).json({ error: 'Industry not found' });
            return;
        }

        // Lấy tên ngành từ kết quả của truy vấn
        const industryName = results[0].INDUSTRY_NAME;

        // Sử dụng tên ngành để lấy danh sách công việc từ API
        const queryJobs = 'SELECT * FROM JOB WHERE INDUSTRY LIKE ?';
        connect.query(queryJobs, [`%${industryName}%`], (error, jobResults) => {
            if (error) {
                console.error('MySQL Error:', error);
                res.status(500).json({ error: 'Server Error' });
                return;
            }
            res.json(jobResults);
        });
    });
};

const getJobListByArea = (req, res) => {
    const area_id = req.query.area_id;

    // Thực hiện truy vấn SQL để lấy tên khu vực dựa trên area_id
    const query = 'SELECT AREA_NAME FROM AREA WHERE ID = ?';
    connect.query(query, [area_id], (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        // Kiểm tra xem có kết quả từ truy vấn hay không
        if (results.length === 0) {
            res.status(404).json({ error: 'Area not found' });
            return;
        }

        // Lấy tên khu vực từ kết quả của truy vấn
        const areaName = results[0].AREA_NAME;

        // Sử dụng tên khu vực để lấy danh sách công việc từ API
        const queryJobs = 'SELECT * FROM JOB WHERE ENROLLMENT_LOCATION LIKE ?';
        connect.query(queryJobs, [`%${areaName}%`], (error, jobResults) => {
            if (error) {
                console.error('MySQL Error:', error);
                res.status(500).json({ error: 'Server Error' });
                return;
            }
            res.json(jobResults);
        });
    });
};

const getJobListByName = (req, res) => {
    const jobName = req.query.jobName;

    const query = 'SELECT * FROM JOB WHERE JOB_NAME LIKE ?';
    connect.query(query, [`%${jobName}%`], (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }

        res.json(results);
    });
};

const createJob = (req, res) => {
    // Kiểm tra lỗi từ Express Validator
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }
    const companyList = getCompanyList();

    const { jobName, industry, location, enrollmentLocation, companyName, salary, genderRequirement, numberOfRecruitment, ageRequirement, probationTime, workWay, experienceRequirement, degreeRequirement, benefits, jobDescription, jobRequirement, deadline } = req.body;


    // Thực hiện truy vấn SQL để tạo mới công việc trong CSDL
    const query = 'INSERT INTO JOB (JOB_NAME, INDUSTRY, LOCATION, POSTED_DATE, ENROLLMENT_LOCATION, SALARY, GENDER_REQUIREMENT, NUMBER_OF_RECRUITMENT, AGE_REQUIREMENT, PROBATION_TIME, WORKWAY, EXPERIENCE_REQUIREMENT, DEGREE_REQUIREMENT, BENEFITS, JOB_DESCRIPTION, JOB_REQUIREMENT, DEADLINE) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)';
    connect.query(query, [jobName, industry, location, enrollmentLocation, salary, genderRequirement, numberOfRecruitment, ageRequirement, probationTime, workWay, experienceRequirement, degreeRequirement, benefits, jobDescription, jobRequirement, deadline], (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        res.status(201).json({ message: 'Job created successfully' });
    });
};

module.exports = {
    getJobList,
    getJobListByIndustry,
    getJobListByArea,
    getJobListByName,
    createJob
}
