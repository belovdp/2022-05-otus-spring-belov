import {Component, Vue} from "vue-property-decorator";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/PageableTable";
import {Book, BookService} from "@/ts/services/BookService";
import {Inject} from "typescript-ioc";

/**
 * Компонент со списком книг
 */
@Component({
    template: `
    <div class="home">
      <pageable-table ref="table"
                      :loader="loadBooks"
                      size="mini" border
                      :default-sort="{prop: 'id', order: 'descending'}"
                      empty-text="Данные отсутствуют или не загружены">
        <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="title" label="Название" sortable="custom"></el-table-column>
        <el-table-column prop="author.name" label="Автор" sortable="custom"></el-table-column>
        <el-table-column prop="genre.name" label="Жанр" sortable="custom"></el-table-column>
        <el-table-column
          label="Действия"
          width="320">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="onBookShow(scope.row.id)">Просмотр</el-button>
            <el-button type="text" size="small" @click="onBookEdit(scope.row.id)">Редактировать</el-button>
            <el-button type="text" size="small" @click="onBookDelete(scope.row.id)">Удалить</el-button>
          </template>
        </el-table-column>
      </pageable-table>
    </div>
  `,
    components: {
        PageableTable,
    },
})
export default class HomeView extends Vue {

    /** Описание справочников */
    $refs!: {
        table: PageableTable<Book>
    };

    /** Сервис по работе с книгами */
    @Inject private readonly bookService: BookService;

    /**
     * Возвращает список книг для таблицы
     * @param pageable запрос на пагинацию
     */
    private async loadBooks(pageable: SpringPageable): Promise<TableData<Book>> {
        return await this.bookService.loadBooks(pageable);
    }

    /**
     * Обработчик просмотра книги
     * @param id идентификатор книги
     */
    private async onBookShow(id: number) {
        await this.$router.push(`/book/${id}`);
    }

    /**
     * Обработчик просмотра книги
     * @param id идентификатор книги
     */
    private async onBookEdit(id: number) {
        await this.$router.push(`/book/editor/${id}`);
    }

    /**
     * Обработчик удаления книги
     * @param id идентификатор книги
     */
    private async onBookDelete(id: number) {
        await this.bookService.deleteBook(id);
        await this.$refs.table.refresh(false);
    }
}