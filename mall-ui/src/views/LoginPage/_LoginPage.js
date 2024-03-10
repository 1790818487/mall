// @vue/component
import axios from "axios";
export default {
  name: 'LoginPage',

  components: {},

  mixins: [],

  props: {},

  data () {
    return {
      formLogin:{
        username:'',
        password:'',
        rememberMe:false,
        uuid:'',
        code:''
      },
      userList:[]
    }
  },

  computed: {},

  watch: {},

  created () {
    this.findUser()
  },

  methods: {
    findUser(){
      axios.get('/api/admin/query').then(
          res=>{
            this.userList=res.data
            console.log(res.data)
          }
      )
    }
  }
}
