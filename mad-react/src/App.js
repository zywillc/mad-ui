import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import LdapList from './LdapList';
import LdapEdit from './LdapEdit';
import Home from './Home';
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import pink from '@material-ui/core/colors/pink';

const mongo_theme = createMuiTheme({
    palette: {
      primary: {
        main: '#a5d6a7',
      },

      secondary: pink,
    },
});
class App extends Component {
  render() {
    return (
      <MuiThemeProvider theme={mongo_theme}>
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/ldaps' exact={true} component={LdapList}/>
          <Route path='/ldaps/:id' component={LdapEdit}/>
        </Switch>
      </Router>
      </MuiThemeProvider>
    )
  }
}

export default App;
