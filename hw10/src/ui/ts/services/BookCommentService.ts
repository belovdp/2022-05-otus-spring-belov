import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import {SpringPageable, TableData} from "@/ts/components/PageableTable";
import {Notification} from "element-ui";

/**
 * Сервис по работе с комментариями книги
 */
@Singleton
@OnlyInstantiableByContainer
export class BookCommentService {



    /**
     * Возвращает список всех книг
     * @param pageable запрос с пагинацией
     */
    async loadBooks(pageable: SpringPageable): Promise<TableData<Book>> {
        return (await axios.get("/books", {
            params: pageable
        })).data;
    }

    /**
     * Удаляет книгу
     * @param id идентификатор книги
     */
    async deleteBook(id: number): Promise<void> {
        await axios.delete(`/book/${id}`);
        Notification.success("Книга успешно удалена");
    }

    /**
     */
    async saveOrUpdateBook(book: Book): Promise<Book> {
        const savedBook = (await axios.post<Book>("/book", book)).data;
        Notification.success("Книга успешно сохранена");
        return savedBook;
    }
}

/** Книга с комментариями */
export type BookWithComments = Book & {
    /** Комментарии */
    comments: {
        /** Идентификатор комментария */
        id: number;
        /** Текст комментария */
        text: string;
    }[];
}

/** Данные о книге */
export type Book = {
    /** Идентификатор книги */
    id: number | null;
    /** Заголовок */
    title: string;
    /** Дата публикации */
    published: string;
    /** Жанр */
    genre: {
        /** Идентификатор жанра */
        id: number | null;
        /** Название жанра */
        name: string;
    }
    /** Автор */
    author: {
        /** Идентификатор автора */
        id: number | null;
        /** Название автора */
        name: string;
    }
}