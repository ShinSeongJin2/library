<template>

    <v-data-table
        :headers="headers"
        :items="loanSummary"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'LoanSummaryView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            loanSummary : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/loanSummaries'))

            temp.data._embedded.loanSummaries.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.loanSummary = temp.data._embedded.loanSummaries;
        },
        methods: {
        }
    }
</script>

