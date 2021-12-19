export abstract class AbstractWindowLocalStorageService {

    private localStorage: Storage;

    constructor() {
        this.localStorage = window.localStorage;
    }

    protected setItem(key: string, value: string | null) {
        localStorage.setItem(key, value!);
    }

    protected getItem(key: string): string | null {
        return this.localStorage.getItem(key);
    }

    protected removeItem(key: string) {
        this.localStorage.removeItem(key);
    }

}