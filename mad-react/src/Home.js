import React, { Component } from 'react';
import './App.css';
import ButtonAppBar from './ButtonAppBar';
import ImageCard from './ImageCard'

class Home extends Component {
  render() {
    return (
      <div>
        <ButtonAppBar/>
          <ImageCard/>
      </div>
    );
  }
}

export default Home;