const { check, validationResult, body } = require('express-validator');
const { postRegisterUser, postLoginUser } = require('../controllers/homeController')
const { getJobList, getJobListByIndustry, getJobListByArea, getJobListByName, createJob } = require('../controllers/job.Controllers')
const { getCompanyList, searchCompany, createCompany, updateCompany, deleteCompany } = require('../controllers/company.Controllers')
const { getIndustryList, updateIndustry, deleteIndustry, createIndustry } = require('../controllers/industry.Controllers')

const express = require('express');
const { getUserList, createUser, updateUser, deleteUser } = require('../controllers/user.Controllers');

const router = express.Router();

router.post('/register', [
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 })
], postRegisterUser);

router.post('/login', [
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 })
], postLoginUser);

router.get('/user/user-list', getUserList);

router.post('/user/create', createUser);

router.put('/user/update', updateUser);

router.delete('/user/delete', deleteUser);

router.get('/jobs/job-list', getJobList);

router.get('/jobs/job-list-by-industry', getJobListByIndustry);

router.get('/jobs/job-list-by-area', getJobListByArea);

router.get('/jobs/job-list-by-name', getJobListByName);


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

], createJob);

router.post('/company/create', [
    body('companyName').isString().notEmpty(),
    body('location').isString().notEmpty(),
    body('staffSize').isString().notEmpty(),
    body('companyDescription').isString().notEmpty()
], createCompany);

router.get('/company/company-list', getCompanyList);

router.get('/company/search', searchCompany);

router.put('/company/update', updateCompany);

router.delete('/company/delete', deleteCompany);

router.get('/industry/industry-list', getIndustryList);

router.post('/industry/create',[
    body('industryName').isString().notEmpty()
], createIndustry);

router.put('/industry/update', updateIndustry);

router.delete('/industry/delete', deleteIndustry);


module.exports = router;
