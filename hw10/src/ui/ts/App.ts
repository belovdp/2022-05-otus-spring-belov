import { Component, Vue } from "vue-property-decorator";

@Component({
    template: `
  <div id="app">
    <el-menu
      router
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-menu-item index="/">Главная</el-menu-item>
      <el-menu-item index="/book/editor/new">Добавить книгу</el-menu-item>
    </el-menu>
      
    <el-container>
      <el-main>
        <router-view :key="$route.path"/>
      </el-main>
    </el-container>
  </div>
  `
})
export default class App extends Vue {
}