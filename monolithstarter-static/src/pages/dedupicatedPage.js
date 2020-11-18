import React, {Component} from 'react';
import {Container} from 'reactstrap';
import Deduplicate from '../components/deduplicated';

class deduplicatedPage extends Component {
  render() {
    return (
      <div className='app'>
        <div className='app-body'>
          <Container fluid className='text-center'>
            <Deduplicate />
          </Container>
        </div>
      </div>
    );
  }
}
export default deduplicatedPage;
