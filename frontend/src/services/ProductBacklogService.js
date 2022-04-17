import Axios from 'axios'

export default class ProductBacklogService {
  constructor () {
    this.instance = Axios.create({
      baseURL: process.env.VUE_APP_BACKEND_BASE_URL + '/backlog',
      timeout: 1000,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
        'Access-Control-Allow-Headers': '*'
      }
    })
  }

  getAll () {
    return this.instance.get('/')
  }
}