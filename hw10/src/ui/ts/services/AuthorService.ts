import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";

/**
 * Сервис по работе с авторами
 */
@Singleton
@OnlyInstantiableByContainer
export class AuthorService {

    /** Список авторов */
    private authors: Author[] | null = null;

    /**
     * Возвращает список авторов из кэша
     */
    async getAuthors(): Promise<Author[]> {
        if (!this.authors) {
            this.authors = await this.loadAuthors();
        }
        return this.authors;
    }

    /**
     * Возвращает список авторов из сервера
     */
    async loadAuthors(): Promise<Author[]> {
        return (await axios.get("/authors")).data;
    }
}

/** Автор */
export type Author = {
    /** Идентификатор жанра */
    id: number;
    /** Название */
    name: string;
}