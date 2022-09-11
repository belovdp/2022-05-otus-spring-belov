import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";

/**
 * Сервис по работе с жанрами
 */
@Singleton
@OnlyInstantiableByContainer
export class GenreService {

    /** Список жанров */
    private genres: Genre[] | null = null;

    /**
     * Возвращает список жанров из кэша
     */
    async getGenres(): Promise<Genre[]> {
        if (!this.genres) {
            this.genres = await this.loadGenres();
        }
        return this.genres;
    }

    /**
     * Возвращает список жанров из сервера
     */
    async loadGenres(): Promise<Genre[]> {
        return (await axios.get("/genres")).data;
    }
}

/** Жанр */
export type Genre = {
    /** Идентификатор жанра */
    id: number;
    /** Название */
    name: string;
}