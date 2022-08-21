import {Component, Vue} from "vue-property-decorator";
import PageableTable from "@/ts/components/PageableTable";
import {Book, BookService} from "@/ts/services/BookService";
import {Inject} from "typescript-ioc";
import {Route} from "vue-router";
import {Author, AuthorService} from "@/ts/services/AuthorService";
import {Genre, GenreService} from "@/ts/services/GenreService";

@Component({
    template: `
    <div class="book">
        <el-form v-if="book" ref="form" :model="book" label-width="120px">
          <el-form-item label="Идентификатор">
            <el-input v-model="book.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="Название">
            <el-input v-model="book.title"></el-input>
          </el-form-item>
          <el-form-item label="Жанр">
            <el-select v-model="book.genre.id" placeholder="Выберите жанр">
              <el-option v-for="genre in genres"
                         :key="genre.id"
                         :label="genre.name"
                         :value="genre.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Автор">
            <el-select v-model="book.author.id" placeholder="Выберите автора">
              <el-option v-for="author in authors"
                         :key="author.id"
                         :label="author.name"
                         :value="author.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Дата публикации">
            <el-date-picker type="date" placeholder="Выберите дату" v-model="book.published" style="width: 100%;"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">Сохранить</el-button>
          </el-form-item>
        </el-form>
    </div>
  `,
    components: {
        PageableTable,
    },
})
export default class BookForm extends Vue {

    /** Типизация роута */
    $route!: {
        params: {
            id: number | "new"
        }
    } & Route;

    /** Сервис по работе с книгами */
    @Inject private readonly bookService: BookService;
    /** Сервис по работе с авторами */
    @Inject private readonly authorService: AuthorService;
    /** Сервис по работе с жанрами */
    @Inject private readonly genreService: GenreService;
    /** Книга */
    private book: Book = this.getNewEmptyBook();
    /** Жанры */
    private genres: Genre[] = [];
    /** Авторы */
    private authors: Author[] = [];

    private async created(): Promise<void> {
        this.genres = await this.genreService.getGenres();
        this.authors = await this.authorService.getAuthors();
        if (this.$route.params.id !== "new") {
            this.book = await this.bookService.getBookById(this.$route.params.id);
        }
    }

    /**
     * Обработчик сохранения книги
     */
    private async onSubmit(): Promise<void> {
        if (this.book) {
            this.book = await this.bookService.saveOrUpdateBook(this.book);
            if (this.$route.params.id === "new") {
                await this.$router.push(`/book/editor/${this.book.id}`);
            }
        }
    }

    /**
     * Возвращает болванку для создания книги
     */
    private getNewEmptyBook(): Book {
        return {
            id: null,
            title: "",
            published: "",
            genre: {
                id: null,
                name: ""
            },
            author: {
                id: null,
                name: ""
            }
        };
    }
}