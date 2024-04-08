const { check, validationResult } = require('express-validator');
const { postRegisterUser, postLoginUser } = require('../controllers/homeController')
const { getJobList, getJobListByIndustry, getJobListByArea, getJobListByName } = require('../controllers/job.Controllers')

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


module.exports = router;
