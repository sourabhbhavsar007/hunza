import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { withCookies } from 'react-cookie';

class CatererEdit extends Component {

  emptyItem = {
    id:null,
    name: '',
    location: {
        city:null,
        street: null,
        postalCode: null,
    },
    capacity: {
      minguest:1,
      maxguest:100
    },
    contactInfo: {
      mobileNumber:null,
      phoneNumber:null,
      email:null,
    }
  };
  
  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem, 
      errors: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleValidation(){
    let fields = this.state.item;
    let errors = {};
    let formIsValid = true;

    //Name
    if(!fields["name"]){
       formIsValid = false;
       errors["name"] = "Cannot be empty";
    }else{
      if(typeof fields["name"] !== "undefined"){
          if(!fields["name"].match(/^[a-zA-Z]+$/)){
            formIsValid = false;
            errors["name"] = "Only letters";
          }        
      }
    }


    //location.city
    if(!fields.location["city"]){
      formIsValid = false;
      errors["city"] = "Cannot be empty";
    }else{
      if(typeof fields.location["city"] !== "undefined"){
          if(!fields.location["city"].match(/^[a-zA-Z]+$/)){
            formIsValid = false;
            errors["city"] = "Only letters";
          }        
      }
    }

    //location.street
    if(!fields.location["street"]){
      formIsValid = false;
      errors["street"] = "Cannot be empty";
    }else{
      if(typeof fields.location["street"] !== "undefined"){
            if(!fields.location["street"].match(/^[_A-z0-9]*((-|\s)*[_A-z0-9])*$/)){
            formIsValid = false;
            errors["street"] = "Only Letters and Number";
          }        
      }
    }

    //capacity.minguest
    if(!fields.capacity["minguest"]){
      formIsValid = false;
      errors["minguest"] = "Cannot be empty";
    }else{
      // if(typeof fields.capacity["minguest"] !== "undefined"){
      //     if(fields.capacity.minguest.match(/^\\d+$/)){
      //       formIsValid = false;
      //       errors["minguest"] = "Only Numbers";
      //     }else{
            let minGuest = fields.capacity["minguest"];
            if(minGuest < 1){
              formIsValid = false;
              errors["minguest"] = "Min Guest should be Positive";
            }
      //     }        
      // }
    }

    //capacity.maxguest
    if(!fields.capacity["maxguest"]){
      formIsValid = false;
      errors["maxguest"] = "Cannot be empty";
    }else{
      // if(typeof fields.capacity["maxguest"] !== "undefined"){
      //     if(fields.capacity["maxguest"].match(/^\\d+$/)){
      //       formIsValid = false;
      //       errors["maxguest"] = "Only Numbers";
      //     }else{
            let minGuest = fields.capacity["minguest"];
            let maxGuest = fields.capacity["maxguest"];
            if(maxGuest < minGuest){
              formIsValid = false;
              errors["maxguest"] = "Max Guest should be greater than Min Guest";
            }else if(maxGuest < 1){
              formIsValid = false;
              errors["maxguest"] = "Max Guest should be Positive";
            }
      //   } 
      // }
    }



    //Email
    if(!fields.contactInfo["email"]){
       formIsValid = false;
       errors["email"] = "Cannot be empty";
    }else{
      if(typeof fields.contactInfo["email"] !== "undefined"){
        let lastAtPos = fields.contactInfo["email"].lastIndexOf('@');
        let lastDotPos = fields.contactInfo["email"].lastIndexOf('.');

        if (!(lastAtPos < lastDotPos && lastAtPos > 0 && fields.contactInfo["email"].indexOf('@@') === -1 && lastDotPos > 2 && (fields.contactInfo["email"].length - lastDotPos) > 2)) {
            formIsValid = false;
            errors["email"] = "Email is not valid";
          }
      }  
    }

   this.setState({errors: errors});
   return formIsValid;
}

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      try {
        const caterer =  await (await fetch(`/caterer/id/${this.props.match.params.id}`, {method: 'GET'})).json();
        this.setState({item: caterer});
      } catch (error) {
        this.props.history.push('/');
      }
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
      
    let item = {...this.state.item};
    if(name === "city" || name === "street" ||  name === "postal_code"){
      item.location[name] = value;
    }else if(name === "minguest" || name === "maxguest"){
      item.capacity[name] = value;
    }else if(name === "mobileNumber" || name === "phoneNumber" ||  name === "email"){
      item.contactInfo[name] = value;
    }else {
      item[name] = value;
    }
    this.setState({item : item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    if(this.handleValidation()){
      await fetch('/caterer/' + (item.id ? '/' + item.id : '') , {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(item)
      });
      this.props.history.push('/caterer');
    }
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Caterer' : 'Add Caterer'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''} onChange={this.handleChange} autoComplete="name" />
            <span style={{color: "red"}}>{this.state.errors["name"]}</span>
          </FormGroup>
          <FormGroup>
            <Label for="street">Street</Label>
            <Input type="text" name="street" id="street" value={item.location.street || ''} onChange={this.handleChange} autoComplete="street-level1"/>
            <span style={{color: "red"}}>{this.state.errors["street"]}</span>
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="city">City</Label>
              <Input type="text" name="city" id="city" value={item.location.city || ''} onChange={this.handleChange} autoComplete="address-level1"/>
              <span style={{color: "red"}}>{this.state.errors["city"]}</span>
            </FormGroup>
            
            <FormGroup className="col-md-6 mb-6">
              <Label for="postalCode">PostalCode</Label>
              <Input type="text" name="postal_code" id="postalCode" value={item.location.postal_code || ''} onChange={this.handleChange} autoComplete="postalCode-level1"/>
            </FormGroup>
          </div>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="minguest">Min Guest</Label>
              <Input type="number" name="minguest" id="minguest" value={item.capacity.minguest || 1} onChange={this.handleChange} autoComplete="minguest-level1"/>
              <span style={{color: "red"}}>{this.state.errors["minguest"]}</span>
            </FormGroup>
            
            <FormGroup className="col-md-6 mb-6">
              <Label for="maxguest">Max Guest</Label>
              <Input type="number" name="maxguest" id="maxguest" value={item.capacity.maxguest || 10} onChange={this.handleChange} autoComplete="maxguest-level1"/>
              <span style={{color: "red"}}>{this.state.errors["maxguest"]}</span>
            </FormGroup>
          </div>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="mobileNumber">Mobile Number</Label>
              <Input type="text" name="mobileNumber" id="mobileNumber" value={item.contactInfo.mobileNumber || ''} onChange={this.handleChange} autoComplete="mobileNumber-level1"/>
              <span style={{color: "red"}}>{this.state.errors["mobileNumber"]}</span>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="phoneNumber">Phone Number</Label>
              <Input type="text" name="phoneNumber" id="phoneNumber" value={item.contactInfo.phoneNumber || ''} onChange={this.handleChange} autoComplete="phoneNumber-level1"/>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="email">Email</Label>
              <Input type="text" name="email" id="email" value={item.contactInfo.email || ''} onChange={this.handleChange} autoComplete="email-level1"/>
              <span style={{color: "red"}}>{this.state.errors["email"]}</span>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/caterer">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withCookies(withRouter(CatererEdit));
