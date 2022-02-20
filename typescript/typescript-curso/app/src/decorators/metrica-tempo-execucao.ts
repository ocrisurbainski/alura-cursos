export function metricaTempoExcecucao(showMetricasEmSegundos: boolean = false) {
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

            console.log(`Método \'${propertyKey}\' tempo de execução: ${calcular(t1, t2, showMetricasEmSegundos)}`);

            return retornoMetodoOriginal;
        };

        return descriptor;
    }
}

function calcular(t1: number, t2: number, showMetricasEmSegundos: boolean): string {

    const diff = t2 - t1;
    if (showMetricasEmSegundos) {
        return `${diff / 1000} segundos`;
    }
    return `${diff} millisegundos`;
}