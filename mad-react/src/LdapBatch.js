import React, { Component } from 'react';
import {Container, Row, Col, FormGroup} from 'reactstrap'
import CSVReader from "react-csv-reader";
import SimpleAppBar from './SimpleAppBar'
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import ReactJson from 'react-json-view'
import Fab from '@material-ui/core/Fab';
import Tooltip from '@material-ui/core/Tooltip';
import AndroidIcon from '@material-ui/icons/Android'
class LdapBatch extends Component {

    mad_list_obj = {
        mads: [],
    };


    constructor(props) {
      super(props);
      this.state = {
        mad_list_obj: this.mad_list_obj,
        hidden: true
      };
      this.handleForce = this.handleForce.bind(this);
      this.handleMongoSubmit = this.handleMongoSubmit.bind(this);
    };

    handleForce(data) {
        const h = data[0];
        let d = data.slice(1);
        let m = d.map( x => x.map((v, i) =>[h[i], v]).reduce((a, e) => {a[e[0]] = e[1]; return a;}, {}));
        let m_obj = {...this.state.mad_list_obj};
        m_obj['mads'] = m.map(e => {e.tag = e.tag.split(" "); return e});
        this.setState({
            mad_list_obj: m_obj,
            hidden: false
        });
    };

    async handleMongoSubmit(event) {
        event.preventDefault();
        const item = this.state.mad_list_obj;
        await fetch('/api/mongo/batch', {
          method: (item.id) ? 'PUT' : 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(item),
        });
      }

    render(){
        return(
            <div>
            <SimpleAppBar/>
            <Container>
            <Row>
            <Col><br></br>Please upload a csv format file: </Col>
            </Row>
            <Row>
            <Col><br></br></Col>
            </Row>
            <div className="row">
            <FormGroup className="col-md-3 mb-3">
            <CSVReader cssClass="react-csv-input" onFileLoaded={this.handleForce}/>
            </FormGroup>
            {!this.state.hidden &&
            <FormGroup className="col-md-1 mb-1">
            <Tooltip title="Submit" aria-label="Submit">
            <Fab color="primary" onClick={this.handleMongoSubmit}>
            <AndroidIcon />
            </Fab>
            </Tooltip>
            </FormGroup>}
            </div>
            </Container>
            {!this.state.hidden &&
            <div>
                <Typography variant="h5" component="h3">
                <br></br>
              The LDAP list in this csv file:
              </Typography>
              <Paper style={{maxHeight: 500, overflow: 'auto'}}>
            <Typography component="p">
            <br></br>
            <ReactJson src={this.state.mad_list_obj} theme="apathy:inverted"/>
            </Typography>
            </Paper>
        </div>
            }
            </div>
        )
    };
}

export default LdapBatch;