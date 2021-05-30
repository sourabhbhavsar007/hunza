import React, { Component } from 'react';
import { Button, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link, withRouter } from 'react-router-dom';
import { withCookies } from 'react-cookie';
import Select from 'react-select';

class CatererList extends Component {
  constructor(props) {
    super(props);
    this.state = {caterer: [],data:{}, isLoading: true, selectedOption: { label: "Page 1", value: 1}, options: []};
  }

  componentDidMount() {
    this.setState({isLoading: true});
    this.populateList();
  }

  populateList(){
    fetch('caterer/list/'+this.state.selectedOption.value)
      .then(response => response.json())
      .then(data => {
          this.setState({caterer: data.content, data:data, isLoading: false});
          this.populateSelect(data);
      })
      .catch(() => this.props.history.push('/'));
  }

  populateSelect(data){
    const totalPages = data.totalPages;
    let options = [];
    for(var i=1;i<=totalPages-1;i++){
      options.push({ value: i, label: 'Page '+i});
    }

    this.setState({options : options});
  }
  
  handleChange = selectedOption => {
    console.log(`Option selected:`, selectedOption);
    fetch('caterer/list/'+selectedOption.value)
    .then(response => response.json())
    .then(data => {
        this.setState({caterer: data.content, data:data, isLoading: false});
    })
    .catch(() => this.props.history.push('/'));
    this.setState({ selectedOption: selectedOption });
  };
  

  render() {
    const {caterer, isLoading, selectedOption,options} = this.state;
    if (isLoading) {
      return <p>Loading...</p>;
    }
    const catererList = caterer.map(c => {
      const location = `${c.location.city || ''} ${c.location.street || ''} ${c.location.postal_code || ''}`;
      const capacity = `${c.capacity.minguest || ''} - ${c.capacity.maxguest || ''}`;
      const contactInfo = `${c.contactInfo.mobileNumber || ''} - ${c.contactInfo.phoneNumber || '' } - ${c.contactInfo.email || ''}`;
      return <tr key={c.Id}>
        <td style={{whiteSpace: 'nowrap'}}>{c.name}</td>
        <td>{location}</td>
        <td>{capacity}</td>
        <td>{contactInfo}</td>
       <td>
          {/* <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/caterer/" + c.Id}>Edit</Button>
          </ButtonGroup> */}
        </td> 
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/caterer/new">Add Caterer</Button>
          </div>
          <div className="float-right">
                <Select value={selectedOption} onChange={this.handleChange} options={options} />
          </div>
          <h3>Caterers List</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th width="20%">Guest (min - max)</th>
              <th width="30%">Contact</th>
              <th width="20%"></th>
            </tr>
            </thead>
            <tbody>
            {catererList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default withCookies(withRouter(CatererList));