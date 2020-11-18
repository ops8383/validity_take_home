import axios from 'axios';

export async function getDuplicateMessage() {
  return (await axios.get('http://localhost:8080/api/duplicates')).data;
}

