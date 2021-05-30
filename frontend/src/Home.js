import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import { withCookies } from 'react-cookie';

class Home extends Component {
  state = {
    isLoading: true
  };
  
  render() {
    const message = this.state.user ?
      <h2>Welcome!</h2> :
      <p>Manage Caterer.</p>;

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          {message}
          <div>
            <Button color="link"><Link to="/caterer">Manage Caterer</Link></Button>
          </div>
        </Container>
      </div>
    );
  }
}

export default withCookies(Home);