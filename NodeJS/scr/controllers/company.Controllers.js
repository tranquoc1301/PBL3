const connect = require('../config/database');
const { validationResult } = require('express-validator');

const getCompanyList = (req, res) => {
    const query = 'SELECT * FROM COMPANY';
    connect.query(query, (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        res.json(results);
    });
}

const searchCompany = (req, res) => {
    const companyName = req.query.companyName;

    if (!companyName) {
        return res.status(400).json({ error: 'Missing companyName parameter' });
    }

    const query = 'SELECT * FROM COMPANY WHERE COMPANY_NAME LIKE ?';
    connect.query(query, [`%${companyName}%`], (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        res.json(results);
    });
}

const createCompany = (req, res) => {
    // Kiểm tra lỗi từ Express Validator
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
    }

    const { companyName, location, staffSize, companyDescription } = req.body;

    // Kiểm tra xem công ty đã tồn tại trong CSDL chưa
    const checkQuery = 'SELECT * FROM COMPANY WHERE COMPANY_NAME = ?';
    connect.query(checkQuery, [companyName], (checkError, checkResults) => {
        if (checkError) {
            console.error('MySQL Error:', checkError);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        // Nếu công ty đã tồn tại, trả về lỗi
        if (checkResults.length > 0) {
            return res.status(400).json({ error: 'Company already exists' });
        }
        // Nếu công ty chưa tồn tại, tiến hành thêm mới
        const insertQuery = 'INSERT INTO COMPANY (COMPANY_NAME, LOCATION, STAFF_SIZE, COMPANY_DESCRIPTION) VALUES (?, ?, ?, ?)';
        connect.query(insertQuery, [companyName, location, staffSize, companyDescription], (insertError, insertResults) => {
            if (insertError) {
                console.error('MySQL Error:', insertError);
                res.status(500).json({ error: 'Server Error' });
                return;
            }
            res.status(201).json({ message: 'Company created successfully' });
        });
    });
}

module.exports = {
    getCompanyList,
    searchCompany,
    createCompany
}