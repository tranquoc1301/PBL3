const mysql = require('mysql2');

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Hoc12345',
    database: 'pbl3_database'
});

db.connect((err) => {
    if(err) throw err;
    console.log('MySQL Connected...');
});

module.exports = db;