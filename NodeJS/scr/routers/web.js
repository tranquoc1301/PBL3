const { check, validationResult, body } = require('express-validator');
const { postRegisterUser, postLoginUser } = require('../controllers/homeController')
const { getJobList, getJobListByIndustry, getJobListByArea, getJobListByName, createJob } = require('../controllers/job.Controllers')
const { getCompanyList, searchCompany, createCompany } = require('../controllers/company.Controllers')

const express = require('express');

const router = express.Router();

router.post('/register', [
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 })
], postRegisterUser)

router.post('/login', [
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 })
], postLoginUser)

router.get('/jobs/jobList', getJobList)

router.get('/jobs/jobListByIndustry', getJobListByIndustry)

router.get('/jobs/jobListByArea', getJobListByArea)

router.get('/jobs/jobListByName', getJobListByName)

router.post('/jobs/create', [
    body('jobName').isString().notEmpty(),
    body('industry').isString().notEmpty(),
    body('location').isString().notEmpty(),
    body('enrollmentLocation').isString().notEmpty(),
    body('salary').isString().notEmpty(),
    body('genderRequirement').isString().notEmpty(),
    body('numberOfRecruitment').isString().notEmpty(),
    body('ageRequirement').isString().notEmpty(),
    body('probationTime').isString().notEmpty(),
    body('workWay').isString().notEmpty(),
    body('experienceRequirement').isString().notEmpty(),
    body('degreeRequirement').isString().notEmpty(),
    body('benefits').isString().notEmpty(),
    body('jobDescription').isString().notEmpty(),
    body('jobRequirement').isString().notEmpty(),
    body('deadline').isDate().notEmpty()

], createJob)

router.post('/company/create',[
    body('companyName').isString().notEmpty(),
    body('location').isString().notEmpty(),
    body('staffSize').isString().notEmpty(),
    body('companyDescription').isString().notEmpty()
], createCompany)

router.get('/company/companyList', getCompanyList)

router.get('/company/search', searchCompany)

module.exports = router;
