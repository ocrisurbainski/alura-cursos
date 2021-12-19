export abstract class abstractWindowLocalStorageService {

    private localStorage: Storage;

    constructor() {
        this.localStorage = window.localStorage;
    }

    setItem(key: string, value: string | null) {
        localStorage.setItem(key, value!);
    }

    getItem(key: string): string | null {
        return this.localStorage.getItem(key);
    }

    removeItem(key: string) {
        this.localStorage.removeItem(key);
    }

}