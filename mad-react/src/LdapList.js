import React, { Component } from 'react';
import { ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import SimpleAppBar from './SimpleAppBar';
import Fab from '@material-ui/core/Fab';
import DeleteIcon from '@material-ui/icons/Delete';
import Icon from '@material-ui/core/Icon';

import AddIcon from '@material-ui/icons/Add';

class LdapList extends Component {

  constructor(props) {
    super(props);
    this.state = {ldaps: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/ldap')
      .then(response => response.json())
      .then(data => this.setState({ldaps: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/ldap/{id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedGroups = [...this.state.ldaps].filter(i => i.id !== id);
      this.setState({ldaps: updatedGroups});
    });
  }

  render() {
    const {ldaps, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const groupList = ldaps.map(group => {
        const namespace = `${group.database}.${group.collection}`
      return <tr key={group.id}>
        <td style={{whiteSpace: 'nowrap'}}>{group.cluster}</td>
        <td>{group.accessType}</td>
        <td>{namespace}</td>
        <td>{group.title}</td>
        <td style={{whiteSspace:'pre-wrap'}}>{group.description}</td>
        <td>
          <ButtonGroup>
            {/* <Button size="sm" color="primary" tag={Link} to={"/ldaps/" + group.id}>Edit</Button> */}
            {/* <Button size="sm" color="danger" onClick={() => this.remove(group.id)}>Delete</Button> */}
            <Fab color="primary" aria-label="Edit" component={Link} to={"/ldaps" + group.id}>
            <Icon>edit_icon</Icon>
            </Fab>
            <Fab aria-label="Delete" color="secondary" onClick={() => this.remove(group.id)}>
            <DeleteIcon />
            </Fab>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <SimpleAppBar/>
        <Container fluid>
          <div className="float-right">
            {/* <Button color="primary" tag={Link} to="/ldaps/new">Add Group</Button> */}
            <Fab color="primary" aria-label="Add" component={Link} to={"/ldaps/new"}>
            <AddIcon />
            </Fab>
          </div>
          <h3>LDAP</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">Cluster</th>
              <th width="10%">Access Type</th>
              <th width="20%">Namespace</th>
              <th width="10%">Title</th>
              <th width="40%">Description</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {groupList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default LdapList;