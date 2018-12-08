import React, { Component } from 'react';
import "./App.css"
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import GitHubIcon from './GithubIcon'


class ButtonAppBar extends Component {

  render() {
    return (
      <div className="root">
        <AppBar position="static" color="primary">
          <Toolbar>
            <Typography variant="h6" color="inherit" className="grow">
              DW MAD UI
            </Typography>
            <IconButton color="inherit" href="https://github.com/zywillc/mad-ui" className="pos">
            <GitHubIcon />
            </IconButton>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default ButtonAppBar;