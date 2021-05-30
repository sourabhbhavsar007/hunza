import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import CatererList from './CatererList';
import CatererEdit from './CatererEdit';
import { CookiesProvider } from 'react-cookie';

class App extends Component {
  render() {
    return (
      <CookiesProvider>
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/caterer' exact={true} component={CatererList}/>
            <Route path='/caterer/:id' component={CatererEdit}/>
          </Switch>
        </Router>
      </CookiesProvider>
    )
  }
}

export default App;