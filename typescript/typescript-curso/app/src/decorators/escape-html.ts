export function escapeHtml() {
    return function(
        target: any,
        propertyKey: string,
        descriptor: PropertyDescriptor
    ) {
        const metodoOriginal = descriptor.value;

        descriptor.value = function(...args: any[]) {

            let retornoMetodoOriginal = metodoOriginal.apply(this, args);

            if (typeof retornoMetodoOriginal === 'string') {
                console.log(`@escpaeHtml aplicado em ${this.constructor.name} método ${propertyKey}`);
                retornoMetodoOriginal = retornoMetodoOriginal.replace(/<script>[\s\S]*?<\/script>/, '');
            }

            return retornoMetodoOriginal;
        }

        return descriptor;
    }
}