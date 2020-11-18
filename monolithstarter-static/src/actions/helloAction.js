import axios from 'axios';

export async function getHelloMessage() {
  return (await axios.get('http://localhost:8080/api/hello')).data;
}

