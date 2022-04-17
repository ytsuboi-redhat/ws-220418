import Vue from 'vue'
import Router from 'vue-router'
import Backlog from '@/components/ProductBacklog'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/backlog',
      name: 'backlog',
      component: Backlog
    }
  ]
})
