import React, { Component } from 'react';
import './App.css'
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import HomeIcon from '@material-ui/icons/Home';
import Typography from '@material-ui/core/Typography';
import { Link } from 'react-router-dom';

class SimpleAppBar extends Component {
  render() {
    return (
      <div className="root">
        <AppBar position="static" color="primary">
          <Toolbar>
          <IconButton color="inherit" aria-label="Home" component={Link} to={"/"}>
            <HomeIcon />
          </IconButton>
            <Typography variant="h6" color="inherit">
              DataSets
            </Typography>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default SimpleAppBar;