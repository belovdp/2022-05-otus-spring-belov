import VueRouter, { RouteConfig } from "vue-router";
import Vue from "vue";
import HomeView from "@/ts/views/HomeView";
import BookView from "@/ts/views/BookView";
import BookForm from "@/ts/views/BookForm";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        name: "home",
        component: HomeView
    },
    {
        path: "/book/:id",
        name: "book",
        component: BookView
    },
    {
        path: "/book/editor/:id(\\d+|new)",
        name: "bookForm",
        component: BookForm
    }
];

const router = new VueRouter({
    routes
});

export default router;
