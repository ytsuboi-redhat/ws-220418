<template>
  <div class="container backlog-container">
    <h1>Backlog</h1>
    <div class="row p-3">
      <div class="col-sm">
        <div id="result">{{ message }}</div>
        <div>
          <table id="product-backlog" class="table">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">name</th>
                <th scope="col">story point</th>
              </tr>
            </thead>
            <draggable tag="tbody" v-model="backlog" @end="end">
              <tr v-for="item in backlog" v-bind:key="item.itemId" v-bind:id="'item-' + item.itemId" @dblclick="routeUpdate(item.itemId)">
                <td class="itemId">{{ item.itemId }}</td>
                <td class="name">{{ item.name }}</td>
                <td class="story-point">{{ convertStoryPoint(item.storyPoint) }}</td>
              </tr>
            </draggable>
          </table>
        </div>
      </div>
    </div>
    <div class="row p-3">
      <!--<button id="register-button" class="btn btn-outline-primary" @mouseup="routeRegister">register</button>-->
    </div>
  </div>
</template>

<script>
import ProductBacklogService from '@/services/ProductBacklogService'

const draggable = require('vuedraggable')
const productBackLogService = new ProductBacklogService()

export default {
  name: 'ProductBacklog',
  props: {
    msg: String
  },
  components: {
    draggable: draggable
  },
  data () {
    return {
      message: '',
      backlog: []
    }
  },
  mounted () {
    productBackLogService.getAll()
      .then(response => {
        this.backlog = response.data
      })
      .catch(response => {
        this.message = response.data
      })
  },
  methods: {
    end () {
      console.log(backlogData)
    },
    routeRegister () {
      this.$router.push('backlog-item')
    },
    routeUpdate (itemId) {
      this.$router.push({
        name: 'backlog-item',
        params: { id: itemId }
      })
    },
    convertStoryPoint (storyPoint) {
      if (storyPoint == -1) {
        return "?"
      }
      return storyPoint
    }
  }
}
</script>

<style>
  .backlog-container {
    padding: 3rem 1.5rem;
    text-align: center;
  }
</style>
