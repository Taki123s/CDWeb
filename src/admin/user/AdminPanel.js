// AdminPanel.js
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Sidebar from './Sidebar'; // Import the Sidebar component
import AddUser from './AddUser'
const AdminPanel = () => {
    return (
        <div>
        <Sidebar/>
        </div>
    );
}

export default AdminPanel;
