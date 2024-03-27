const express = require('express');
const { check, validationResult } = require('express-validator');
const bcrypt = require('bcrypt');
const bodyParser = require('body-parser');
const db = require('./db');
const port = 3000;

const app = express();
app.use(bodyParser.json());

app.post('/register', [
    check('email').isEmail().normalizeEmail(),
    check('password').isLength({ min: 8 })
], (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(422).json({ errors: errors.array() });
    }

    const { email, password, privilege_id } = req.body;

    // Kiểm tra xem người dùng đã tồn tại hay chưa
    const checkUserQuery = 'SELECT * FROM user WHERE email = ?';
    db.query(checkUserQuery, [email], (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Failed to register user !' });
        }

        // Nếu đã tồn tại người dùng có cùng email trong cơ sở dữ liệu
        if (results.length > 0) {
            return res.status(409).json({ error: 'User already exists !' });
        }

        // Nếu không có người dùng nào cùng email tồn tại, tiến hành đăng ký
        bcrypt.hash(password, 10, (err, hashedPassword) => {
            if (err) {
                return res.status(500).json({ error: 'Failed to register user !' });
            }

            const insertUserQuery = 'INSERT INTO user (EMAIL, PASSWORD, PRIVILEGE_ID, CREATE_AT) VALUES (?, ?, 0, NOW())';
            db.query(insertUserQuery, [email, hashedPassword], (err, result) => {
                if (err) {
                    return res.status(500).json({ error: 'Failed to register user !' });
                }
                res.status(201).json({ message: 'User registered successfully !' });
            });
        });
    });
});

app.post('/login', (req, res) => {
    const { email, password } = req.body;

    const Query = 'SELECT * FROM user WHERE email = ?';
    db.query(Query, [email], (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Failed to authenticate user !' });
        }
        //Kiểm tra người dùng có tồn tại không
        if (results.length == 0) {
            return res.status(401).json({ error: 'Invalid email or password !' });
        }

        const user = results[0];

        bcrypt.compare(password, user.password, (err, isMathch) => {
            if (err) {
                return res.status(500).json({ error: 'Failed to authenticate user !' });
            }
            if (!isMathch) {
                return res.status(401).json({ error: 'Invalid email or password !' });
            }
            else
                res.status(200).json({ message: 'Login successfully !' });
        });
    });
});

app.listen(port, () => {
    console.log(`Server started on port ${port}`);
});
