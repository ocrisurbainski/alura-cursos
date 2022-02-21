export function domInject(selector: string) {
    return function(
        target: any,
        propertyKey: string
    ) {
        let element: HTMLElement;

        const getter = function() {
            if (!element) {
                element = document.querySelector(selector) as HTMLElement;
            }
            return element;
        }

        Object.defineProperty(target, propertyKey, {
            get: getter
        });
    }
}