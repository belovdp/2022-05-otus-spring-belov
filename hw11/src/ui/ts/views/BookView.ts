import {Component, Vue} from "vue-property-decorator";
import PageableTable from "@/ts/components/PageableTable";
import {Book, BookComment, BookService} from "@/ts/services/BookService";
import {Inject} from "typescript-ioc";
import {Route} from "vue-router";

@Component({
    template: `
    <div class="book">
        <el-descriptions v-if="book"
                         title="Информация о книге"
                         border
                         :column="1"
                         :labelStyle="{'font-weight': 'bold', 'width': '250px'}">
            <el-descriptions-item label="Идентификатор">{{book.id}}</el-descriptions-item>
            <el-descriptions-item label="Название">{{book.title}}</el-descriptions-item>
            <el-descriptions-item label="Жанр">{{book.genre.name}}</el-descriptions-item>
            <el-descriptions-item label="Автор">{{book.author.name}}</el-descriptions-item>
            <el-descriptions-item label="Комментарии">
                <ul>
                  <li v-for="comment in book.comments" :key="comment.id">
                      {{comment.text}}
                  </li>
                </ul>
            </el-descriptions-item>
        </el-descriptions>
        <el-button type="primary" @click="dialogFormVisible = true">Добавить комментарий</el-button>
        <el-dialog title="Добавление комментария" :visible.sync="dialogFormVisible">
          <el-form :model="comment">
            <el-form-item label="Текст">
              <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="comment.text"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">Отмена</el-button>
            <el-button type="primary" @click="onAddComment">Сохранить</el-button>
          </span>
        </el-dialog>
    </div>
  `,
    components: {
        PageableTable,
    },
})
export default class BookView extends Vue {

    /** Типизация роута */
    $route!: {
        params: {
            id: number
        }
    } & Route;

    /** Сервис по работе с книгами */
    @Inject private readonly bookService: BookService;
    /** Книга */
    private book: Book | null = null;
    /** Признак отображения диалога добавления комментария */
    private dialogFormVisible = false;
    private comment: BookComment = this.getEmptyComment();

    private async created(): Promise<void> {
        this.book = await this.bookService.getBookById(this.$route.params.id);
    }

    private async onAddComment(): Promise<void> {
        await this.bookService.addComment(this.comment, this.$route.params.id);
        this.dialogFormVisible = false;
        this.comment = this.getEmptyComment();
        this.book = await this.bookService.getBookById(this.$route.params.id);
    }

    private getEmptyComment() {
        return {
            id: null,
            text: ""
        };
    }
}