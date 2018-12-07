import React, { Component } from 'react';
import "./App.css"
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';


class ButtonAppBar extends Component {

  render() {
    return (
      <div className="root">
        <AppBar position="static" color="primary">
          <Toolbar>
            <Typography variant="h6" color="inherit" className="grow">
              DW MAD UI
            </Typography>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default ButtonAppBar;