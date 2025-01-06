<template>

    <v-data-table
        :headers="headers"
        :items="loanDetails"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'LoanDetailsView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            loanDetails : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/loanDetails'))

            temp.data._embedded.loanDetails.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.loanDetails = temp.data._embedded.loanDetails;
        },
        methods: {
        }
    }
</script>

