import React, {Component} from 'react';
import {Container} from 'reactstrap';
import Duplicate from '../components/duplicated';


class duplicatedPage extends Component {
  render() {
    return (
      <div className='app'>
        <div className='app-body'>
          <Container fluid className='text-center'>
            <Duplicate />
          </Container>
        </div>
      </div>
    );
  }
}

export default duplicatedPage;
