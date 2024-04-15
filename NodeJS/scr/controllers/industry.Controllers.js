const connect = require('../config/database');

const getIndustryList = (req, res) => {
    const query = 'SELECT * FROM INDUSTRY';
    connect.query(query, (error, results) => {
        if (error) {
            console.error('MySQL Error:', error);
            res.status(500).json({ error: 'Server Error' });
            return;
        }
        res.json(results);
    });
}

const updateIndustry = async (req, res) => {
    const { id } = req.params;
    const { industryName } = req.body;

    try {
        const result = await connect.promise().query('UPDATE INDUSTRY SET INDUSTRY_NAME = ? WHERE INDUSTRY_ID = ?', [industryName, id]);
        if (result[0].affectedRows === 0) {
            return res.status(404).send({ error: 'Industry not found' });

        }
        res.status(200).send({ message: 'Industry updated successfully' });

    } catch (err) {
        console.error('Error updating industry:', err);
        return res.status(500).send({ error: 'Error updating industry', message: err.message });
    }
}

const deleteIndustry = async (req, res) => {
    const { id } = req.params;

    try {
        const result = await connect.promise().query('DELETE FROM INDUSTRY WHERE INDUSTRY_ID = ?', [id]);
        
        if (result[0].affectedRows === 0) {
            return res.status(404).send({ error: 'Industry not found' });
        }

        res.status(200).send({ message: 'Industry deleted successfully' });
    } catch (err) {
        console.error('Error deleting industry:', err);
        return res.status(500).send({ error: 'Error deleting industry', message: err.message });
    }
};

module.exports = {
    getIndustryList,
    updateIndustry,
    deleteIndustry
}