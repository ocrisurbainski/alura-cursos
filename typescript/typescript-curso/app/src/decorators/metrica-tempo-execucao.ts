export function metricaTempoExcecucao() {
    return function(
        target: any,
        propertyKey: any,
        descriptor: PropertyDescriptor
    ) {
        const metodoOriginal = descriptor.value;

        descriptor.value = function(...args: any[]) {
            const t1 = performance.now();

            const retornoMetodoOriginal = metodoOriginal.apply(this, args);

            const t2 = performance.now();

            console.log(`Método \'${propertyKey}\' tempo de execução: ${(t2 - t1) / 1000}`);

            return retornoMetodoOriginal;
        };

        return descriptor;
    }
}