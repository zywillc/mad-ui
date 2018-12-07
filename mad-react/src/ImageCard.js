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
import PlaylistAddIcon from '@material-ui/icons/PlaylistAdd'


class ImageCard extends Component {
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
            <ListItem button component={Link} to={"/ldaps/new"}>
            <ListItemIcon>
            <Fab size="small" color="default" aria-label="Edit">
                <PlaylistAddIcon />
            </Fab>
            </ListItemIcon>
            <ListItemText inset primary="New LDAP" />
            </ListItem>
            </List>
            </CardActions>
        </Card>
        );
    }
}

export default ImageCard;