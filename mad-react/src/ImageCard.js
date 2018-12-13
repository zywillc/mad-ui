import React, { Component } from 'react';
import {Row, Col} from 'reactstrap';
import "./App.css"
import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import ListItemText from '@material-ui/core/ListItemText';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListIcon from '@material-ui/icons/List';
import Fab from '@material-ui/core/Fab';
import { Link } from 'react-router-dom';
import CreateIcon from '@material-ui/icons/Create'
import AttachFileIcon from '@material-ui/icons/AttachFile';
import NoteAddIcon from '@material-ui/icons/NoteAddOutlined'
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
class ImageCard extends Component {

    state = {
        open: true,
      };
    
      handleClick = () => {
        this.setState(state => ({ open: !state.open }));
      };

    render(){
        return (
        <Card>
             <Row>
                <Col><br></br></Col>
            </Row>
            <CardActionArea>
            <CardMedia>
            <img src={require('./static/images/mongo.png')} alt="robomongo-logo" className='media'/>
            </CardMedia>
            <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                Datawarehouse DataSet Access LDAP UI
                </Typography>
                <Typography component="p">
                Create LDAP for access to Datawarehouse DataSets
                </Typography>
            </CardContent>
            </CardActionArea>
            <CardActions>
            <List component="nav">
            <ListItem button component={Link} to={"/ldaps"}>
            <ListItemIcon>
            <Fab size="small" color="primary" aria-label="List">
                <ListIcon />
            </Fab>
            </ListItemIcon>
            <ListItemText inset primary="List LDAP" />
            </ListItem>

            <ListItem button onClick={this.handleClick}>
            <ListItemIcon>
            <Fab size="small" color="default" aria-label="Edit">
                <NoteAddIcon />
            </Fab>
            </ListItemIcon>
            <ListItemText inset primary="New LDAP" />
            {this.state.open ? <ExpandLess /> : <ExpandMore />}
            </ListItem>
            <Collapse in={this.state.open} timeout="auto" unmountOnExit>
            <List component="div" disablePadding>
            <ListItem button component={Link} to={"/ldaps/new"}>
            <ListItemIcon>
                <CreateIcon />
            </ListItemIcon>
            <ListItemText inset primary="Single" />
            </ListItem>

            <ListItem button component={Link} to={"/batch"}>
            <ListItemIcon>
                <AttachFileIcon/>
            </ListItemIcon>
            <ListItemText inset primary="Batch" />
            </ListItem>
            </List>
            </Collapse>
            </List>
            </CardActions>
        </Card>
        );
    }
}

export default ImageCard;