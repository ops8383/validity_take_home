import axios from 'axios';

export async function getDeduplicateMessage() {
  return (await axios.get('http://localhost:8080/api/deduplicates')).data;
}

