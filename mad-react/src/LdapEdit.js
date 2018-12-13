import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Container, Row, Col, Form, FormGroup, Input, Label } from 'reactstrap';
import SimpleAppBar from './SimpleAppBar';
import TagsInput from 'react-tagsinput'
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import FolderSharedIcon from '@material-ui/icons/FolderShared';
import 'react-tagsinput/react-tagsinput.css'
import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';
import CancelIcon from '@material-ui/icons/CancelOutlined'
import SvgIcon from '@material-ui/core/SvgIcon';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button';
import ReactJson from 'react-json-view'



import 'react-tagsinput/react-tagsinput.css'

function MongoIcon(props) {
  return (
    <SvgIcon {...props} viewBox="0 0 100 100">
     <path d="M51.167,0c-1.959,11.753-22.245,21.568-22.245,50.217c0,25.908,14.264,33.808,17.203,35.033 C51.511,79.617,50.677,33.056,51.167,0z"/>
     <path d="M51.875,6.375c2.5,6.625,18.918,23.273,18.918,43.842s-9.06,30.118-16.895,35.749c0,0,0,7.897,0,9.611s-1.377,2.571-2.234,2.571s-2.418-0.796-2.418-2.143s0-9.427,0-9.427C56.346,83.395,52.242,27.066,51.875,6.375z"/>
    </SvgIcon>
  );
}
class LdapEdit extends Component {

  tags = [];

  emptyItem = {
    cluster: '',
    accessType: '',
    database: '',
    collection: '',
    title: '',
    description: '',
    tags: [],
    approvalPolicy: ''
  };


  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      tags: this.tags,
      open: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleTagsChange = this.handleTagsChange.bind(this);
    this.handleLdapSubmit = this.handleLdapSubmit.bind(this);
    this.handleMongoSubmit = this.handleMongoSubmit.bind(this);
    this.handleAtlasSubmit = this.handleAtlasSubmit.bind(this);
  }

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const group = await (await fetch(`/api/ldap/${this.props.match.params.id}`)).json();
      this.setState({item: group});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    item['tags'] = [...this.state.tags];
    this.setState({item: item});
  }

  handleTagsChange(tags) {
    let item = {...this.state.item};
    item['tags'] = tags;
    this.setState({item: item});
    this.setState({tags: tags});
  }

  async handleLdapSubmit(event) {
    event.preventDefault();
    const item = this.state.item;
    await fetch('/api/ldap', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/ldaps');
  }

  async handleMongoSubmit(event) {
    event.preventDefault();
    const item = this.state.item;
    await fetch('/api/mongo', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
  }

  async handleAtlasSubmit(event) {
    event.preventDefault();
    const item = this.state.item;
    await fetch('/api/access', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/ldaps');
  }

  render() {
    const {item, tags} = this.state;
    const title = <h2>{item.id ? 'Edit LDAP' : 'Add LDAP'}</h2>;

    return <div>
      <SimpleAppBar/>
      <Container>
      <Row>
          <Col><br></br></Col>
       </Row>
        {title}
        <Form>
          <FormGroup>
            <Label for="cluster">Cluster</Label>
            <Input type="select" name="cluster" id="cluster" value={item.cluster} onChange={this.handleChange}>
              <option>datawarehouse</option>
              <option>datawarehouseprod</option>
              <option>3datawarehouse-segmen</option>
              <option>datawarehouseatlasmonitoring</option>
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="accessType">Access Type</Label>
            <Input type="select" name="accessType" id="accessType" value={item.accessType} onChange={this.handleChange}>
              <option>read</option>
              <option>readWrite</option>
            </Input>
          </FormGroup>

          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="database">DataBase</Label>
              <Input type="text" name="database" id="database" value={item.database || ''}
                     onChange={this.handleChange} placeholder="e.g. dw, source_prod"/>
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="collection">Collection</Label>
              <Input type="text" name="collection" id="collection" value={item.collection || ''}
                     onChange={this.handleChange} placeholder="e.g. Project_c"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3">
              <Label for="title">Tile</Label>
              <Input type="text" name="title" id="title" value={item.title || ''}
                     onChange={this.handleChange} placeholder="e.g. dw-dw-dw"/>
            </FormGroup>
            <FormGroup className="col-md-4 mb-3">
              <Label for="approvalPolicy">Approval Policy</Label>
              <Input type="select" name="approvalPolicy" id="approvalPolicy" value={item.approvalPolicy} onChange={this.handleChange}>
                <option></option>
                <option>PRIMARY_ONLY</option>
                <option>PRIMARY_AND_SECONDARY</option>
              </Input>
            </FormGroup>
          </div>

          <FormGroup>
            <Label for="description">Description</Label>
            <Input type="text" name="description" id="description" value={item.description || ''}
                   onChange={this.handleChange} placeholder="Please give your description"/>
          </FormGroup>

          <FormGroup>
          <TagsInput value={tags} onChange={this.handleTagsChange} />
          </FormGroup>
          <div className="row">
          <FormGroup className="col-md-1 mb-1">
            <Tooltip title="LDAP" aria-label="LDAP">
            <Fab color="default" type="submit" onSubmit={this.handleLdapSubmit}>
            <FolderSharedIcon />
            </Fab>
            </Tooltip>
          </FormGroup>

          <FormGroup className="col-md-1 mb-1">
            <Tooltip title="Mongo" aria-label="Mongo">
            <Fab color="primary" onClick={this.handleClickOpen}>
            <MongoIcon />
            </Fab>
            </Tooltip>
            <Dialog
          open={this.state.open}
          onClose={this.handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description">
          <DialogTitle id="alert-dialog-title"></DialogTitle>
          <DialogContent>
            <DialogContentText color="secondary" id="alert-dialog-description">
              Please check and confirm this new LDAP:<br></br>
              <ReactJson src={this.state.item} />
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="secondary">
              No
            </Button>
            <Button type="submit" onSubmit={this.handleMongoSubmit} onClick={this.handleClose} color="primary" autoFocus>
              Yes
            </Button>
          </DialogActions>
        </Dialog>
          </FormGroup>

          <FormGroup className="col-md-1 mb-1">
            <Tooltip title="Atlas" aria-label="Atlas">
            <Fab color="primary" >
            <CloudUploadIcon />
            </Fab>
            </Tooltip>
            <Dialog
          open={this.state.open}
          onClose={this.handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description">
            <DialogTitle id="alert-dialog-title"></DialogTitle>
          <DialogContent>
            <DialogContentText color="secondary" id="alert-dialog-description">
              Please check and confirm this new LDAP:<br></br>
              <ReactJson src={this.state.item} />
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="secondary">
              No
            </Button>
            <Button type="submit" onSubmit={this.handleMongoSubmit} onClick={this.handleClose} color="primary" autoFocus>
              Yes
            </Button>
          </DialogActions>
        </Dialog>
          </FormGroup>

          <FormGroup className="col-md-1 mb-1">
          <Tooltip title="Cancel" aria-label="Cancel">
            <Fab color="secondary" component={Link} to="/ldaps">
            <CancelIcon />
            </Fab>
            </Tooltip>
          </FormGroup>
          </div>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(LdapEdit);